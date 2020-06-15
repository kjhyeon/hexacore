package com.hexa.core.model.cal.inf;

import java.util.List;
import java.util.Map;

import com.hexa.core.dto.CalDTO;


public interface Calendar_IDao {

	public List<CalDTO> selectEventsCal(Map<String, Object> map);
	
	public int insertEventsCal(CalDTO CDto);
}
