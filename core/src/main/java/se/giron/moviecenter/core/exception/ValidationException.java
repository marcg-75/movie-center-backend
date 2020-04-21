package se.giron.moviecenter.core.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 162192172625L;
	private String messageCode;
    private Object[] objects;

    public ValidationException(String messageCode) {
        this.messageCode = messageCode;
    }

    public ValidationException(String messageCode, Object... objects) {
        this.messageCode = messageCode;
        this.objects = objects;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public Object[] getObjects() {
        return objects;
    }
}