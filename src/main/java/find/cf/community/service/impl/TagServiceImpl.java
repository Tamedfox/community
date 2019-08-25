package find.cf.community.service.impl;

import find.cf.community.dto.TagDTO;
import find.cf.community.mapper.TagCategoryMapper;
import find.cf.community.mapper.TagsMapper;
import find.cf.community.model.TagCategory;
import find.cf.community.model.Tags;
import find.cf.community.model.TagsExample;
import find.cf.community.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagsMapper tagsMapper;

    @Autowired
    private TagCategoryMapper tagCategoryMapper;

    /**
     * 查找所有的tag
     * @return
     */
    @Override
    public List<TagDTO> findAllTags() {
        List<TagCategory> tagCategories = tagCategoryMapper.selectByExample(null);
        List<TagDTO> list = new ArrayList<>();
        for (TagCategory tagCategory : tagCategories) {
            TagDTO tagDTO = new TagDTO();
            TagsExample example = new TagsExample();
            example.createCriteria().andParentIdEqualTo(tagCategory.getId());
            List<Tags> tags = tagsMapper.selectByExample(example);
            BeanUtils.copyProperties(tagCategory,tagDTO);
            tagDTO.setTags(tags);
            list.add(tagDTO);
        }
        return list;
    }

    /**
     * 查找传入的tags是否有修改
     * @param tags
     * @return
     */
    @Override
    public boolean isValid(String tags) {
        String[] split = StringUtils.split(tags, "-");
        List<Tags> list = tagsMapper.selectByExample(null);
        List<String> invalidList = Arrays.stream(split).filter(t -> !list.contains(t)).collect(Collectors.toList());
        if(invalidList.size() == 0){
            return false;
        }
        return true;
    }
}
