package ${package}.network;

import java.util.ArrayList;

public interface ArrayResponseHandler <E> {
	
	public static int PARSING_ERROR = -1;
	
	public void onSuccess(ArrayList<E> object);
	public void onFailure(int errorCode, String error);
}
