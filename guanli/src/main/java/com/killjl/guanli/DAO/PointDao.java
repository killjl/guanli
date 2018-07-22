package com.killjl.guanli.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.killjl.guanli.model.Point;

@Mapper

public interface PointDao {
	String TABLE_NAME=" point ";
	String INSERT_FIELDS=" stdid,sum,major,minor,art,economy,literature,language,sport,innovation ";
	String SELECT_FIELDS=" id,stdid,sum,major,minor,art,economy,literature,language,sport,innovation ";

	@Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
		") values (#{stdid},#{sum},#{major},#{minor},#{art},#{economy},#{literature},"
		+ "#{language},#{sport},#{innovation})"})
	int addPoint(Point point);
	
	@Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where stdid=#{stdid} "})
	Point selectByStdId(int stdid);
	
	int updatePoint(@Param("stdid") int stdid, @Param("sum") int sum, @Param("major") int major,
			@Param("minor") int minor, @Param("art") int art,@Param("economy") int economy, @Param("literature") int literature
			, @Param("language") int language, @Param("sport") int sport, @Param("innovation") int innovation);
}
