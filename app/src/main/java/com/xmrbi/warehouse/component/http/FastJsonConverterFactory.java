package com.xmrbi.warehouse.component.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Created by wzn on 2018/4/24.
 */

public class FastJsonConverterFactory extends Converter.Factory {
    private final SerializeConfig serializeConfig;

    private FastJsonConverterFactory(SerializeConfig serializeConfig) {
        if (serializeConfig == null) {
            throw new NullPointerException("serializeConfig == null");
        } else {
            this.serializeConfig = serializeConfig;
        }
    }

    public static FastJsonConverterFactory create() {
        return create(SerializeConfig.getGlobalInstance());
    }

    public static FastJsonConverterFactory create(SerializeConfig serializeConfig) {
        return new FastJsonConverterFactory(serializeConfig);
    }

    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FastJsonRequestBodyConverter(this.serializeConfig);
    }

    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastJsonResponseBodyConvert(type);
    }

    class FastJsonResponseBodyConvert<T> implements Converter<ResponseBody, T> {
        private Type type;

        public FastJsonResponseBodyConvert(Type type) {
            this.type = type;
        }

        public T convert(ResponseBody value) throws IOException {
            String result = value.string();
            //如果返回的结果要求是String就返回string
            String typeClassName = type.toString();
            if (typeClassName.substring(typeClassName.lastIndexOf(".") + 1, typeClassName.length()).equals("String")) {
                return (T) result;
            }
            if (typeClassName.contains("com.xmrbi.warehouse.component.http.Response")) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (!json.getBoolean("success")) {
                        json.put("data", null);
                    }
                    result = json.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return JSON.parseObject(result, this.type, new Feature[0]);
        }
    }

    class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private SerializeConfig serializeConfig;

        public FastJsonRequestBodyConverter(SerializeConfig serializeConfig) {
            this.serializeConfig = serializeConfig;
        }

        public RequestBody convert(T value) throws IOException {
            return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value, this.serializeConfig, new SerializerFeature[0]));
        }
    }

}

