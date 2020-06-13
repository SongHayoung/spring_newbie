package com.newbie.Spring_Newbie.User.dao;

import com.newbie.Spring_Newbie.User.sqlService.jaxb.SqlType;
import com.newbie.Spring_Newbie.User.sqlService.jaxb.Sqlmap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.Unmarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-jaxbContext.xml")
public class OxmTest {
    @Autowired Unmarshaller unmarshaller;

    @Test
    public void unmarshallSqlMap() throws XmlMappingException, IOException {
        Source xmlSource = new StreamSource(getClass().getResourceAsStream("/sqlmap.xml"));

        Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);

        List<SqlType> sqlTypeList = sqlmap.getSql();
        assertThat(sqlTypeList.size(), is(3));
        assertThat(sqlTypeList.get(0).getKey(), is("add"));
        assertThat(sqlTypeList.get(1).getKey(), is("get"));
        assertThat(sqlTypeList.get(2).getKey(), is("delete"));
    }
}
