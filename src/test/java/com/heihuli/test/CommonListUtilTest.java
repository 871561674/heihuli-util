package com.heihuli.test;

import com.heihuli.domain.ListObj;
import com.heihuli.util.CommonJacksonUtil;
import com.heihuli.util.CommonListUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author heihuli
 */
public class CommonListUtilTest {

    /**
     * CommonListUtil.listToFieldList
     * @throws Exception
     */
    @Test
    public void test() throws Exception {

        ListObj list1 = new ListObj(1, "heihuli", null);
        ListObj list2 = new ListObj(2, "heihuli2", Arrays.asList(new ListObj(4, "heihuli4", null)));
        ListObj list3 = new ListObj(3, "heihuli3", Arrays.asList(new ListObj(5, "heihuli5", null)));

        List<ListObj> listObjs = new ArrayList<>();
        listObjs.add(list1);
        listObjs.add(list2);
        listObjs.add(list3);
        System.out.println(CommonJacksonUtil.OM.valueToTree(listObjs));
        // [{"id":1,"name":"heihuli","list":null},{"id":2,"name":"heihuli2","list":[{"id":4,"name":"heihuli4","list":null}]},{"id":3,"name":"heihuli3","list":[{"id":5,"name":"heihuli5","list":null}]}]

        List<String> list = CommonListUtil.listToFieldList(listObjs, "getName");
        System.out.println(CommonJacksonUtil.OM.valueToTree(list));
        // ["heihuli","heihuli2","heihuli3"]

        List<String> list0 = CommonListUtil.listToFieldList(listObjs, "getList");
        System.out.println(CommonJacksonUtil.OM.valueToTree(list0));
        // ["[com.heihuli.domain.ListObj@7e2d773b]","[com.heihuli.domain.ListObj@2173f6d9]"]
    }

    /**
     * CommonListUtil.listToUniqueFieldList
     */
    @Test
    public void test2() {
        ListObj list1 = new ListObj(1, "heihuli", null);
        ListObj list2 = new ListObj(2, "heihuli", Arrays.asList(new ListObj(4, "heihuli4", null)));
        ListObj list3 = new ListObj(3, "heihuli", Arrays.asList(new ListObj(5, "heihuli5", null)));

        List<ListObj> listObjs = new ArrayList<>();
        listObjs.add(list1);
        listObjs.add(list2);
        listObjs.add(list3);
        System.out.println(CommonJacksonUtil.OM.valueToTree(listObjs));
        // [{"id":1,"name":"heihuli","list":null},{"id":2,"name":"heihuli","list":[{"id":4,"name":"heihuli4","list":null}]},{"id":3,"name":"heihuli","list":[{"id":5,"name":"heihuli5","list":null}]}]

        List<String> list = CommonListUtil.listToUniqueFieldList(listObjs, "getName");
        System.out.println(CommonJacksonUtil.OM.valueToTree(list));
        // ["heihuli"]

    }

}
