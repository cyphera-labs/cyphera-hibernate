package io.cyphera.hibernate.compat;

import io.cyphera.hibernate.CypheraHolder;
import jakarta.persistence.AttributeConverter;

/**
 * JPA AttributeConverter base class — compatibility mode for non-Hibernate JPA providers.
 *
 * For Hibernate users, prefer the @CypheraProtect annotation (see CypheraIntegrator)
 * or the explicit @Type approach (see CypheraType).
 *
 * Usage: subclass with the configuration name:
 *   @Converter
 *   public class SsnConverter extends CypheraConverter {
 *       public SsnConverter() { super("ssn"); }
 *   }
 */
public abstract class CypheraConverter implements AttributeConverter<String, String> {

    private final String configurationName;

    protected CypheraConverter(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public String convertToDatabaseColumn(String value) {
        if (value == null) return null;
        return CypheraHolder.get().protect(value, configurationName);
    }

    @Override
    public String convertToEntityAttribute(String dbValue) {
        if (dbValue == null) return null;
        return CypheraHolder.get().access(dbValue);
    }
}
