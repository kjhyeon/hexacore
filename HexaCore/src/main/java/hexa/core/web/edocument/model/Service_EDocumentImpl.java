package hexa.core.web.edocument.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service_EDocumentImpl implements IService_EDocument{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IDao_EDocument dao;
}
