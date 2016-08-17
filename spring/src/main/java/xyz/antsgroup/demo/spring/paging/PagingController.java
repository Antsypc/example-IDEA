package xyz.antsgroup.demo.spring.paging;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("/paging")
public class PagingController {

    @Resource(name = "studentService")
    private StudentService studentService;

    @RequestMapping("/students")
    public String student(Map<String, List> map) {
        map.put("heads", Arrays.asList("学号","密码","姓名","学院","专业","班级","性别","入学年份","籍贯","电话","邮箱"));
        return "paging/students";
    }

    @RequestMapping(value = "/students", produces = "application/json")
    @ResponseBody
    public List<StudentEntity> studentData(DataTablesRequest tablesRequest, String department, String major, String classes) {
        DataTablesResponse<StudentEntity> response = new DataTablesResponse<>();
        PageQuery page = new PageQuery();
        page.setAsc(tablesRequest.getOrder().get(0).getDir());
        page.setCurrent(tablesRequest.getStart());
        page.setPerPage(tablesRequest.getLength());
        page.setOrder(tablesRequest.getOrder().get(0).getDir());

        System.out.println("Request query page:\n" + page + "\n" + department + "\n" + major + "\n" + classes);

        List<StudentEntity> list = studentService.getStudentsByCondition(page, department, major, classes);
        System.out.println(list.toString());
        return list;
    }
}