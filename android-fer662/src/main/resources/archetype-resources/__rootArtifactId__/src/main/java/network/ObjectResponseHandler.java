package ${package}.network;

public interface ObjectResponseHandler<E> {
	
	public static int PARSING_ERROR = -1;
	
	public void onSuccess(E object);
	public void onFailure(int errorCode, String error);
}
