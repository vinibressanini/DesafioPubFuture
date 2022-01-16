package dao;

import java.util.List;

public interface DAO<E> {

	E select(Class<E> classe, long id) throws Exception;
	
	E insert( E objeto) throws Exception;
	
	E delete( Class<E> classe, long id) throws Exception;
	
	E update( E objeto) throws Exception;
	
	List<E> selectAll( Class<E> classe) throws Exception;
	
}
