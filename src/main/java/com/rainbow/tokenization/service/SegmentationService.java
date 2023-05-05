package com.rainbow.tokenization.service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SegmentationService {

    public List<String> segment(String text) {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("输入不应该为空串哦。");
        }
        List<Term> termList = HanLP.segment(text);
        List<String> resultList = new ArrayList<>();
        for (Term term : termList) {
            resultList.add(term.word);
        }
        return resultList;
    }

}
