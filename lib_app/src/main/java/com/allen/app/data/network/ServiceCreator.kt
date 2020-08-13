package com.allen.app.data.network

import android.annotation.SuppressLint
import android.text.TextUtils
import com.allen.base.utils.LogUtils
import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * 网络基础服务配置。
 *
 */
object ServiceCreator {
    private val TAG = ServiceCreator::class.java.name
    private const val BASE_URL = "http://m2.qiushibaike.com/"

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(LogInterceptor(TAG))
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(getGson()))

    fun getGson(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Date::class.java, DateFormatter())
        builder.registerTypeAdapter(String::class.java, StringDefaultAdapter())
        builder.registerTypeAdapter(Integer::class.java, IntegerDefaultAdapter())
        builder.registerTypeAdapter(java.lang.Long::class.java, LongDefaultAdapter())
        builder.registerTypeAdapter(java.lang.Boolean::class.java, BooleanDefaultAdapter())
        builder.registerTypeAdapter(java.lang.Double::class.java, DoubleDefaultAdapter())
        builder.registerTypeAdapter(Float::class.java, FloatDefaultAdapter())
        builder.registerTypeAdapter(Int::class.java, IntRuntimeJsonDeserializerAdapter())
        builder.registerTypeAdapter(Long::class.java, LongRuntimeJsonDeserializerAdapter())
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        builder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        builder.serializeNulls()
        return builder.create()
    }

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val request = original.newBuilder().apply {
                header("Accept-Encoding", "identity")
                header("User-Agent", "qiushibalke_11.10.5_WIFI_auto_19")
            }.build()
            return chain.proceed(request)
        }
    }

    class IntegerDefaultAdapter : TypeAdapter<Int>() {
        override fun write(out: JsonWriter, value: Int?) {
            try {
                if (value != null) {
                    out.value(value)
                } else {
                    out.value(0)
                }
            } catch (e: Exception) {
                out.value(0)
            }
        }

        override fun read(jsonReader: JsonReader): Int {
            try {
                if (jsonReader.peek() === JsonToken.NULL) {
                    jsonReader.nextNull()
                    return 0
                }
                val tempValue: String? = jsonReader.nextString()
                if (tempValue != null && !TextUtils.isEmpty(tempValue) && TextUtils.isDigitsOnly(
                        tempValue
                    )
                ) {
                    return Integer.parseInt(tempValue)
                } else {
                    return 0
                }
            } catch (e: Throwable) {
                return 0
            }
        }
    }

    class LongDefaultAdapter : TypeAdapter<Long>() {
        override fun write(out: JsonWriter, value: Long?) {
            try {
                if (value != null) {
                    out.value(value)
                } else {
                    out.value(0L)
                }
            } catch (e: Throwable) {
                out.value(0L)
            }
        }

        override fun read(jsonReader: JsonReader): Long {
            try {
                val value: Long?
                if (jsonReader.peek() === JsonToken.NULL) {
                    jsonReader.nextNull()
                    return 0L
                }
                value = jsonReader.nextLong()
                return value
            } catch (e: Throwable) {
                return 0L
            }
        }
    }


    class StringDefaultAdapter : TypeAdapter<String>() {
        override fun write(out: JsonWriter, value: String?) {
            if (value != null)
                out.value(value)
            else
                out.value("")
        }

        override fun read(jsonReader: JsonReader): String {
            return if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull()
                ""
            } else {
                jsonReader.nextString().toString()
            }
        }
    }

    class BooleanDefaultAdapter : TypeAdapter<Boolean>() {
        override fun write(out: JsonWriter, value: Boolean?) {
            try {
                if (value != null) {
                    out.value(value)
                } else {
                    out.value(false)
                }
            } catch (e: Exception) {
                out.value(false)
            }
        }

        override fun read(jsonReader: JsonReader): Boolean {
            try {
                val value: Boolean?
                if (jsonReader.peek() === JsonToken.NULL) {
                    jsonReader.nextNull()
                    return false
                }
                value = jsonReader.nextBoolean()
                return value
            } catch (e: Throwable) {
                return false
            }
        }
    }

    class DoubleDefaultAdapter : TypeAdapter<Double>() {
        override fun write(out: JsonWriter, value: Double?) {
            try {
                if (value != null) {
                    out.value(value)
                } else {
                    out.value(0.0)
                }
            } catch (e: Exception) {
                out.value(0.0)
            }
        }

        override fun read(jsonReader: JsonReader): Double {
            try {
                val value: Double?
                if (jsonReader.peek() === JsonToken.NULL) {
                    jsonReader.nextNull()
                    return 0.0
                }
                value = jsonReader.nextDouble()
                return value
            } catch (e: Throwable) {
                return 0.0
            }
        }
    }

    class FloatDefaultAdapter : TypeAdapter<Float>() {
        override fun write(out: JsonWriter, value: Float?) {
            try {
                if (value != null) {
                    out.value(value)
                } else {
                    out.value(0.0F)
                }
            } catch (e: Exception) {
                out.value(0.0F)
            }
        }

        override fun read(jsonReader: JsonReader): Float {
            try {
                val value: Float?
                if (jsonReader.peek() === JsonToken.NULL) {
                    jsonReader.nextNull()
                    return 0.0F
                }
                value = jsonReader.nextDouble().toFloat()
                return value
            } catch (e: Throwable) {
                return 0.0F
            }
        }
    }

    class DateFormatter @SuppressLint("SimpleDateFormat")
    constructor() : JsonDeserializer<Date>, JsonSerializer<Date> {
        private val formats = arrayOfNulls<DateFormat>(1)

        init {
            this.formats[0] = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val timeZone = TimeZone.getTimeZone("Zulu")
            val var2 = this.formats
            val var3 = var2.size

            (0 until var3)
                .map { var2[it] }
                .forEach { it?.timeZone = timeZone }
        }

        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): Date? {
            val value = json.asString
            if (!TextUtils.isEmpty(value) && value.length != 1) {
                val var5 = this.formats
                val var6 = var5.size
                var var7 = 0

                while (var7 < var6) {
                    val format = var5[var7]
                    if (format != null) {
                        try {
                            synchronized(format) {
                                return format.parse(value)
                            }
                        } catch (var12: ParseException) {
                            android.util.Log.e(TAG, "日期转换错误， $value", var12)
                            ++var7
                        }
                    }
                }

                return Date(0L)
            } else {
                return null
            }
        }

        override fun serialize(
            date: Date,
            type: Type,
            context: JsonSerializationContext
        ): JsonElement {
            val primary = this.formats[0]
            var formatted = ""
            if (primary != null) {
                synchronized(primary) {
                    formatted = primary.format(date)
                }
            }

            return JsonPrimitive(formatted)
        }
    }

    class IntRuntimeJsonDeserializerAdapter : JsonDeserializer<Int>, JsonSerializer<Int> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): Int {
            var runtime: Int
            try {
                runtime = json.asInt
            } catch (e: NumberFormatException) {
                runtime = 0
            }

            return runtime
        }

        override fun serialize(
            src: Int,
            typeOfSrc: Type,
            context: JsonSerializationContext
        ): JsonElement {
            return JsonPrimitive(src)
        }
    }

    class LongRuntimeJsonDeserializerAdapter : JsonDeserializer<Long>, JsonSerializer<Long> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
        ): Long {
            var runtime: Long
            try {
                runtime = json.asLong
            } catch (e: NumberFormatException) {
                runtime = 0
            }

            return runtime
        }

        override fun serialize(
            src: Long,
            typeOfSrc: Type,
            context: JsonSerializationContext
        ): JsonElement {
            return JsonPrimitive(src)
        }
    }
}


