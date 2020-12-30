package com.lnsoft.activiti.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xuyuan
 */
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1164915995547754991L;

    @Getter
    public static final String GENERAL_SUCCESS_CODE = "success.general";
    @Getter
    public static final String GENERAL_FAILURE_CODE = "failure.general";


    public static final Boolean GENERAL_SUCCESS_STATUS = true;
    public static final Boolean GENERAL_FAILURE_STATUS = false;

    @Getter
    @Setter
    private Boolean status = GENERAL_SUCCESS_STATUS;
    @Getter
    @Setter
    private String error = GENERAL_SUCCESS_CODE;
    @JsonIgnore
    private Object[] args = null;
    @Getter
    @Setter
    private T result = null;
    @Getter
    @Setter
    private DataType dataType = null;
    @Getter
    @Setter
    private String nextID = null;

    public Response() {

    }

    public Response(Boolean status, String error,T data) {
        this.status = status;
        this.error = error;
        this.result = data;
    }

    public Response(Boolean status, String error, String nextId, DataType dataType,T data) {
        this.status = status;
        this.error = error;
        this.nextID = nextId;
        this.dataType = dataType;
        this.result = data;
    }

    public static <T> Response<T> yes(final T data, DataType dataType) {
        return new Response(GENERAL_SUCCESS_STATUS, GENERAL_SUCCESS_CODE,null,dataType, data);
    }

    public boolean success() {
        return status;
    }

    public static <T> Response<T> yes() {
        return new Response(GENERAL_SUCCESS_STATUS, GENERAL_SUCCESS_CODE, true);
    }

    public static <T> Response<T> yes(final T data) {
        return new Response(GENERAL_SUCCESS_STATUS, GENERAL_SUCCESS_CODE,null,null, data);
    }

    public static <T> Response<T> yes(final T data, final String code) {
        return new Response(GENERAL_SUCCESS_STATUS, code,null,null, data);
    }

    public static <T> Response<T> yes(final T data, final Boolean status) {
        return new Response(status, GENERAL_SUCCESS_CODE,null,null, data);
    }

    public static <T> Response<T> yes(final T data, final Boolean status, final String code) {
        return new Response(status, code,null,null, data);
    }

    public static <T> Response<T> yes(final T data, final Boolean status,final String nextId, final String code) {
        return new Response(status, code,nextId,null, data);
    }

    public static <T> Response<T> yes(final T data, final Boolean status,final String nextId,final DataType dataType, final String code) {
        return new Response(status, code,nextId,dataType, data);
    }

    public static <T> Response<T> no() {
        return new Response(GENERAL_FAILURE_STATUS, GENERAL_FAILURE_CODE, null);
    }

    public static <T> Response<T> no(final String code) {
        return new Response(GENERAL_FAILURE_STATUS, code, null);
    }

    public static <T> Response<T> no(final Boolean status) {
        return new Response(status, GENERAL_FAILURE_CODE, null);
    }

    public static <T> Response<T> no(final Boolean status, final String code) {
        return new Response(status, code, null);
    }

    public static <T> Response<T> no(final Boolean status, final String code, final String message) {
        return new Response(status, code,  null);
    }

    public static <T> Response<T> no(final Boolean status, final String code, final T data) {
        return new Response(status, code, data);
    }

    public static <T> Response<T> no(Response response) {
        return response;
    }

    public static <T> Response<T> no(final T data) {
        return new Response<T>(GENERAL_FAILURE_STATUS, GENERAL_FAILURE_CODE, data);
    }

    public void args(Object... args) {
        this.args = args;
    }

    public Object[] args() {
        return this.args;
    }

    public static class DataType {

        public static final String IS_ZIP = "Y";

        public static final String FORMAT = "E";


        @Getter
        @Setter
        private String isZip = IS_ZIP;

        @Getter
        @Setter
        private String format = FORMAT;


        public DataType(){}


        public DataType(String isZip,String format){
            this.isZip = isZip;
            this.format = format;
        }


    }
}
