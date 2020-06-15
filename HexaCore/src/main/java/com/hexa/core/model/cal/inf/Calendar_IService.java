package com.hexa.core.model.cal.inf;

import java.util.List;
import java.util.Map;

import com.hexa.core.dto.CalDTO;

public interface Calendar_IService {

	public List<CalDTO> selectEventsCal(Map<String, Object> map);
	
	public int insertEventsCal(CalDTO CDto);
	
	public int deleteEventsCal(String title);
}
