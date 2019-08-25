package find.cf.community.service;

import find.cf.community.dto.TagDTO;

import java.util.List;

public interface TagService {


    /**
     * 查询所有tags
     * @return
     */
    List<TagDTO> findAllTags();

    /**
     * 判断传入的tag是否有修改
     * @param tags
     * @return
     */
    boolean isValid(String tags);
}
