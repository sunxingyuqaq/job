package com.boot.job.db.generator.servie;


import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.job.db.generator.entity.GeneratorConfig;

/**
 * @author MrBird
 */
public interface IGeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询
     *
     * @return GeneratorConfig
     */
    GeneratorConfig findGeneratorConfig();

    /**
     * 修改
     *
     * @param generatorConfig generatorConfig
     */
    void updateGeneratorConfig(GeneratorConfig generatorConfig);

}
