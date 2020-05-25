package hexa.core.web.edocument.model;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Dao_EDocumentImpl implements IDao_EDocument{

	@Autowired
	private SqlSessionTemplate sqlSession;
	private final String NS="hexa.core.web.sqls.";
}
