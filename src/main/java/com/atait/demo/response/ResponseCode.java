package com.atait.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCode {

    //success response codes start with zeros
    public static final ResponseCodeDto SUCCESS = new ResponseCodeDto("0000","Action is successful");

    //error codes start with ones
    public static final ResponseCodeDto FAIL = new ResponseCodeDto("1999","Something went wrong");

    public static final ResponseCodeDto WRONG_REQUEST = new ResponseCodeDto("1888","Wrong request");

    public static final ResponseCodeDto INTERNAL_SERVER_ERROR = new ResponseCodeDto("1998","Internal server error");

    public static final ResponseCodeDto RESOURCE_DOES_NOT_EXIST = new ResponseCodeDto("1997","The resource you request does not exist.");


    @AllArgsConstructor
    public static class ResponseCodeDto{

        private String code;
        private String msg;

        public String code() {
            return code;
        }
        public String msg() {
            return msg;
        }
    }
}
