package Server.Pororing.apiPayload.exception.handler;


import Server.Pororing.apiPayload.code.BaseErrorCode;
import Server.Pororing.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
