package com.nielsen.desafiofullstack.app;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class JacksonConfiguration {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

		return builder -> {

			builder.serializationInclusion(JsonInclude.Include.NON_NULL);
			
			// formatter
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			// deserializers
			builder.deserializers(new LocalDateDeserializer(dateFormatter));
			builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

			// serializers
			builder.serializers(new LocalDateSerializer(dateFormatter));
			builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
		};

	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer zonedDateTimeCustomizer() {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return builder -> {
			builder.serializationInclusion(JsonInclude.Include.NON_NULL);

			builder.serializers(new StdSerializer<>(ZonedDateTime.class) {
				private static final long serialVersionUID = 7267001379918924374L;

				@Override
				public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers)
						throws IOException {
					gen.writeString(value.format(dateTimeFormatter));
				}
			});
			builder.deserializers(new StdDeserializer<>(ZonedDateTime.class) {
				private static final long serialVersionUID = 154543596456722486L;

				@Override
				public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JacksonException {
					return ZonedDateTime.parse(p.getText(), dateTimeFormatter.withZone(ZoneId.systemDefault()));
				}
			});
		};

	}
}
