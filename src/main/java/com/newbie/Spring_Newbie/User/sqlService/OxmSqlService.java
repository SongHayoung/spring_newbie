package com.newbie.Spring_Newbie.User.sqlService;

import com.newbie.Spring_Newbie.User.dao.UserDao;
import com.newbie.Spring_Newbie.User.sqlService.jaxb.SqlType;
import com.newbie.Spring_Newbie.User.sqlService.jaxb.Sqlmap;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;

import javax.annotation.PostConstruct;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class OxmSqlService implements SqlService{
    private final BaseSqlService baseSqlService = new BaseSqlService();
    private final OxmSqlReader oxmSqlReader = new OxmSqlReader();
    private SqlRegistry sqlRegistry = new HashMapSqlRegistry();

    public void setSqlRegistry(SqlRegistry sqlRegistry) {
        this.sqlRegistry = sqlRegistry;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.oxmSqlReader.setUnmarshaller(unmarshaller);
    }

    public void setSqlmap(Resource sqlmapFile) {
        this.oxmSqlReader.setSqlmap(sqlmapFile);
    }

    @PostConstruct
    public void loadSql() {
        this.baseSqlService.setSqlReader(this.oxmSqlReader);
        this.baseSqlService.setSqlRegistry(this.sqlRegistry);

        this.oxmSqlReader.read(this.sqlRegistry);
    }

    public String getSql(String key) throws SqlRetrievalFailureException {
        return this.baseSqlService.getSql(key);
    }
    private class OxmSqlReader implements SqlReader{
        private Resource sqlmap = new ClassPathResource("sqlmap.xml",UserDao.class);
        private Unmarshaller unmarshaller;
        private final String DEFAULT_SQLMAP_FILE = "sqlmap.xml";
        private String sqlmapFile = DEFAULT_SQLMAP_FILE;

        public void setUnmarshaller(Unmarshaller unmarshaller) { this.unmarshaller = unmarshaller; }
        public void setSqlmap(Resource sqlmap) { this.sqlmap = sqlmap; }

        public void read(SqlRegistry registry){
            try{
                Source source = new StreamSource(sqlmap.getInputStream());
                Sqlmap sqlmap = (Sqlmap)this.unmarshaller.unmarshal(source);

                for(SqlType sql : sqlmap.getSql()){
                    sqlRegistry.registerSql(sql.getKey(), sql.getValue());
                }
            } catch (IOException e){
                throw new IllegalArgumentException(this.sqlmap.getFilename() + "을 가져올 수 없습니다", e);
            }
        }

    }
}
