package cn.taotao.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbContent;

import com.taotao.service.TbContentService;
import com.taotao.utils.JsonUtils;

import cn.taotao.utils.AD1;

@Controller
public class IndexController {
	@Autowired
	private TbContentService tbContentService;

	@RequestMapping("index")
	public String index(Model model){
		
List<TbContent> findByCategoryid = tbContentService.findByCategoryid(89L);
		
		List<AD1> list = new ArrayList();
		for (TbContent tbContent : findByCategoryid) {
			AD1 ad = new AD1();
			ad.setAlt(tbContent.getTitle());
			ad.setSrc(tbContent.getPic());
			ad.setSrcB(tbContent.getPic2());
			ad.setHref(tbContent.getUrl());
			ad.setHeight(240);
			ad.setHeightB(240);
			ad.setWidth(550);
			ad.setWidthB(670);
			list.add(ad);
		}
		String json = JsonUtils.objectToJson(list);
		model.addAttribute("ad1", json);
		return "index";
	}
	
	
	
}
