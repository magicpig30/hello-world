package com.epoch.bdp.businessobject.controller;

import java.util.List;

import com.epoch.bdp.businessobject.model.vo.botypedefine.BOCheckSelfCodeVO;
import com.epoch.bdp.businessobject.service.common.IBOUtilService;
import com.epoch.bdp.businessobject.util.BOColumnCodeExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.epoch.bdp.businessobject.model.vo.base.BOAreaDesignVO;
import com.epoch.bdp.businessobject.model.vo.base.BOAreaVO;
import com.epoch.bdp.businessobject.model.vo.base.BOAreaViewVO;
import com.epoch.bdp.businessobject.service.base.IBOAreaService;
import com.epoch.infrastructure.i18n.util.I18nUtils;
import com.epoch.infrastructure.log.model.annotation.LogModule;
import com.epoch.infrastructure.log.model.annotation.LogUserOperate;
import com.epoch.infrastructure.util.model.enums.ModuleEnum;
import com.epoch.infrastructure.log.model.enums.OperateTypeEnum;
import com.epoch.infrastructure.util.model.PublicStaticI18nEnum;
import com.epoch.infrastructure.util.model.ResultValue;

/**
 * 
 * 业务对象区域控制类 
 * @author 刘远
 * @date 2019年2月14日 下午3:02:39 
 *
 */
@RestController
@RequestMapping("/bo/boArea")
@LogModule(ModuleEnum.BUSINESS_OBJECT)
public class BOAreaController {

    @Autowired
    private IBOAreaService boAreaService;
    @Autowired
    private IBOUtilService boUtilService;

    /**
     * 
     * 根据业务对象查询区域
     * @param boDefineId 业务对象id
     * @return
     */
    @RequestMapping(value = "/selectAreaByBODefineId", method = RequestMethod.POST)
    @LogUserOperate(value = "根据业务对象查询区域", operateType = OperateTypeEnum.SELECT)
    public ResultValue<List<BOAreaViewVO>> selectAreaByBODefineId(String boDefineId){
    	List<BOAreaViewVO> areaList = boAreaService.selectAreaViewVOByDefineId(boDefineId,null,null);
    	return new ResultValue<>(areaList , true , null);
    }
    
    /**
     * 
     * 根据业务对象和区域id查询区域字段
     * @param boDefineId 业务对象id
     * @param boAreaId 区域id
     * @return
     */
    @RequestMapping(value = "/selectAreaAndFieldByAreaId", method = RequestMethod.POST)
    @LogUserOperate(value = "根据业务对象和区域id查询区域字段", operateType = OperateTypeEnum.SELECT)
    public ResultValue<List<BOAreaDesignVO>> selectAreaAndFieldByAreaId(String boDefineId , String boAreaId){
    	List<BOAreaDesignVO> boAreaList = boAreaService.selectAreaAndField(boDefineId, boAreaId);
    	return new ResultValue<>(boAreaList , true , null);
    }
    
    /**
     * 
     * 根据业务对象查询主表区域字段
     * @param boDefineId 业务对象id
     * @return
     */
    @RequestMapping(value = "/selectMainAreaAndFieldByAreaId", method = RequestMethod.POST)
    @LogUserOperate(value = "根据业务对象查询主表区域字段", operateType = OperateTypeEnum.SELECT)
    public ResultValue<List<BOAreaDesignVO>> selectMainAreaAndFieldByAreaId(String boDefineId){
    	List<BOAreaVO> areaList = boAreaService.selectAreaByBODefineId(boDefineId);
    	String mainAreaId = "";
    	
    	if(areaList != null){
    		for (int i = 0; i < areaList.size(); i++) {
				if(areaList.get(i).getMain()){
					mainAreaId = areaList.get(i).getBoAreaId();
				}
			}
    	}else{
    		return null;
    	}
    	
    	List<BOAreaDesignVO> areaFieldList = boAreaService.selectAreaAndField(boDefineId, mainAreaId);
    	return new ResultValue<>(areaFieldList , true , null);
    }

    /**
     * 
     * 新增区域
     * @param boAreaVO 区域对象
     * @return
     */
    @RequestMapping(value = "/insertArea", method = RequestMethod.POST)
    @LogUserOperate(value = "插入区域", operateType = OperateTypeEnum.INSERT)
    public ResultValue<BOAreaVO> insertArea(@RequestBody @Validated BOAreaViewVO boAreaVO){
    	BOAreaViewVO area = boAreaService.insertArea(boAreaVO);
    	return new ResultValue<>(area , true , I18nUtils.translate(PublicStaticI18nEnum.SAVE_SUCCESS));
    }

    /**
     * 
     * 修改区域
     * @param areaVO 区域对象
     * @return
     */
    @RequestMapping(value = "/updateArea", method = RequestMethod.POST)
    @LogUserOperate(value = "修改区域", operateType = OperateTypeEnum.UPDATE)
    public ResultValue<BOAreaVO> updateArea(@RequestBody @Validated BOAreaVO areaVO){
    	BOAreaVO area = boAreaService.updateArea(areaVO);
    	return new ResultValue<>(area , true , I18nUtils.translate(PublicStaticI18nEnum.UPDATE_SUCCESS));
    }

    /**
     * 
     * 删除区域
     * @param boAreaVO 区域对象
     * @return
     */
    @RequestMapping(value = "/deleteArea", method = RequestMethod.POST)
    @LogUserOperate(value = "删除区域", operateType = OperateTypeEnum.DELETE)
    public ResultValue<BOAreaVO> deleteArea(String boAreaId){
    	boAreaService.deleteArea(boAreaId , true);
    	return new ResultValue<>(null , true , I18nUtils.translate(PublicStaticI18nEnum.DELETE_SUCCESS));
    }
    
    @RequestMapping(value = "/selectAreaLanguageByBODefineId", method = {RequestMethod.GET,RequestMethod.POST})
	@LogUserOperate(value = "根据业务对象获取区域字段，包括多语言", operateType = OperateTypeEnum.SELECT)
	public ResultValue<List<BOAreaDesignVO>> selectAreaLanguageByBODefineId(String boDefineId) {
		List<BOAreaDesignVO> list = boAreaService.selectAreaLanguageByBODefineId(boDefineId);
		return new ResultValue<List<BOAreaDesignVO>>(list , true , null);
	}

    @RequestMapping(value = "/selectAreaLanguageByBODefineId4Parent", method = {RequestMethod.GET,RequestMethod.POST})
    @LogUserOperate(value = "根据业务对象获取区域字段，包括多语言", operateType = OperateTypeEnum.SELECT)
    public ResultValue<List<BOAreaDesignVO>> selectAreaLanguageByBODefineId(String boDefineId,String areaId) {
        List<BOAreaDesignVO> list = boAreaService.selectParentAndSelfByAreaId(areaId,boDefineId);
        return new ResultValue<List<BOAreaDesignVO>>(list , true , null);
    }


    @RequestMapping(value = "/selectAreaLanguageByBODefineIdNew", method = {RequestMethod.GET,RequestMethod.POST})
    @LogUserOperate(value = "根据业务对象获取区域字段，包括多语言", operateType = OperateTypeEnum.SELECT)
    public ResultValue<List<BOAreaDesignVO>> selectAreaLanguageByBODefineIdNew(String boDefineId,String boAreaId) {
        List<BOAreaDesignVO> list = boAreaService.selectSubTableArea(boDefineId,boAreaId);
        return new ResultValue<List<BOAreaDesignVO>>(list , true , null);
    }
}
