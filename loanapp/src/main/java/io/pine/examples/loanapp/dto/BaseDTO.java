package io.pine.examples.loanapp.dto;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class BaseDTO {

    /**
     * Method to get a String representing the current state of the DTO.
     *
     * @return retStr is string of fields listed in the DTO
     */
    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
