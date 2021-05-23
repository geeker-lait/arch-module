package org.arch.framework.automate.generater.reader.xmind.parser;

import org.arch.framework.automate.common.module.Module;
import org.arch.framework.automate.common.module.Project;
import org.arch.framework.automate.generater.reader.xmind.meta.Attached;
import org.arch.framework.automate.generater.reader.xmind.meta.Children;
import org.arch.framework.automate.generater.reader.xmind.meta.JsonRootBean;
import org.arch.framework.automate.generater.reader.xmind.meta.RootTopic;
import org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.MODULE;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.PKG;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.PROJECT;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindApiParser.generateApi;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindModelParser.generateModel;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.getTiTleType;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.removeNewlines;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.splitInto3Parts;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindProjectParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindProjectParser.class);

    /**
     * 解析 xmind 为 {@link Project} 的对象.
     *
     * @param root    xmind 的 {@link JsonRootBean}
     * @param project {@link Project} 用于存放 xmind 的解析结果
     */
    public static void generate(@NonNull JsonRootBean root, @NonNull Project project) {
        try {
            String title = root.getTitle().trim();
            TiTleType tiTleType = getTiTleType(title);
            if (isNull(tiTleType)) {
                generateOfRootTopic(root.getRootTopic(), project, tiTleType);
                return;
            }
            if (PROJECT.equals(tiTleType)) {
                String[] splits = splitInto3Parts(title);
                project.setName(splits[1].trim());
                project.setDescr(removeNewlines(splits[2].trim()));
                generateOfRootTopic(root.getRootTopic(), project, tiTleType);
            }
            else if (MODULE.equals(tiTleType)) {
                String[] splits = splitInto3Parts(title);
                Module module = new Module();
                module.setTyp(splits[0].trim());
                module.setName(splits[1].trim());
                module.setComment(removeNewlines(splits[2].trim()));
                project.getModules().add(module);
                generateOfRootTopic(root.getRootTopic(), project, tiTleType);
            }
        } catch (Exception e) {
            generateOfRootTopic(root.getRootTopic(), project, null);
        }
    }

    private static void generateOfRootTopic(@NonNull RootTopic rootTopic, @NonNull Project project,
                                            @Nullable TiTleType pTiTleType) {
        String title = rootTopic.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        if (isNull(pTiTleType)) {
            if (nonNull(tiTleType) && PROJECT.equals(tiTleType)) {
                String[] splits = splitInto3Parts(title);
                project.setName(splits[1].trim());
                project.setDescr(removeNewlines(splits[2].trim()));
            }
            Children children = rootTopic.getChildren();
            generate(children, project.getModules(), title, tiTleType);
            return;
        }
        if (PROJECT.equals(pTiTleType)) {
            if (nonNull(tiTleType) && MODULE.equals(tiTleType)) {
                Module module = new Module();
                project.getModules().add(module);
                Children children = rootTopic.getChildren();
                if (isNull(children)) {
                    //noinspection ConstantConditions
                    generate(children, project.getModules(), title, tiTleType);
                    return;
                }
                generateOfChildren(children, project.getModules(), module, MODULE, title);
            }
        }
        else if (MODULE.equals(pTiTleType)) {
            Children children = rootTopic.getChildren();
            if (nonNull(children)) {
                List<Module> modules = project.getModules();
                generateOfChildren(children, modules, modules.get(0));
            }
        }
    }

    /**
     * 根据 {@link Attached} 的内容生成相应的规格内容, 添加到 {@link Module}.
     *
     * @param attached   {@link Attached}
     * @param moduleList {@link Module} list
     */
    private static void generateOfAttached(@NonNull Attached attached, @NonNull List<Module> moduleList) {
        String title = attached.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        Children children = attached.getChildren();
        if (isNull(children)) {
            return;
        }
        generate(children, moduleList, title, tiTleType);
    }

    private static void generate(@Nullable Children children, @NonNull List<Module> moduleList,
                                 @NonNull String title, @Nullable TiTleType pTiTleType) {
        if (isNull(children)) {
            return;
        }
        if (nonNull(pTiTleType) && MODULE.equals(pTiTleType)) {
            Module module = new Module();
            moduleList.add(module);
            generateOfChildren(children, moduleList, module, MODULE, title);
            return;
        }
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            generateOfAttached(attached, moduleList);
        }
    }

    /**
     * 根据 {@link Attached} 的内容生成相应的规格内容, 添加到 {@link Module}.
     *
     * @param attached   {@link Attached}
     * @param moduleList {@link Module} list
     * @param module     {@link Module}
     */
    static void generateOfAttachedWithModule(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                                    @NonNull Module module) {
        String title = attached.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        Children children = attached.getChildren();
        if (isNull(children)) {
            if (nonNull(tiTleType) && PKG.equals(tiTleType)) {
                generatePkg(null, moduleList, module, title, tiTleType);
            }
            return;
        }
        generateWithModule(children, moduleList, module, title, tiTleType);
    }

    static void generateWithModule(@NonNull Children children, @NonNull List<Module> moduleList,
                                           @NonNull Module module,
                                           @NonNull String title, @Nullable TiTleType tiTleType) {
        if (isNull(tiTleType)) {
            List<Attached> attachedList = children.getAttached();
            if (isNull(attachedList) || attachedList.size() == 0) {
                return;
            }
            for (Attached attached : attachedList) {
                generateOfAttachedWithModule(attached, moduleList, module);
            }
            return;
        }
        if (MODULE.equals(tiTleType)) {
            Module newModule = new Module();
            moduleList.add(newModule);
            generateOfChildren(children, moduleList, newModule, MODULE, title);
            return;
        }
        generateOfChildren(children, moduleList, module, tiTleType, title);
    }

    static void generateOfChildren(@NonNull Children children, @NonNull List<Module> moduleList,
                                          @NonNull Module module) {
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            generateOfAttachedWithModule(attached, moduleList, module);
        }
    }

    /**
     * 根据 {@link Children} 的内容生成相应的规格内容, 添加到 {@link Module}.
     *
     * @param children   {@link Children}
     * @param moduleList {@link Module} list
     * @param module     {@link Module}
     * @param pTiTleType {@link TiTleType}
     * @param pTitle     pTitle
     */
    static void generateOfChildren(@NonNull Children children, @NonNull List<Module> moduleList,
                                          @NonNull Module module, @Nullable TiTleType pTiTleType, @NonNull String pTitle) {
        if (isNull(pTiTleType)) {
            generateOfChildren(children, moduleList, module);
            return;
        }

        switch (pTiTleType) {
            case MODULE:
                String[] splits = splitInto3Parts(pTitle);
                //noinspection AlibabaUndefineMagicConstant
                if (splits.length == 3) {
                    module.setTyp(splits[0].trim())
                          .setName(splits[1].trim())
                          .setComment(removeNewlines(splits[2].trim()));
                }
                generateOfChildren(children, moduleList, module);
                return;
            case DATABASE:
                XmindDatabaseParser.generateDatabase(children, moduleList, module, pTitle);
                return;
            case API:
                generateApi(children, moduleList, module, pTiTleType, pTitle);
                return;
            case ENTITY:
                generateModel(children, moduleList, module, pTitle, null, null);
                return;
            case PKG:
                generatePkg(children, moduleList, module, pTitle, pTiTleType);
                return;
            default:
                generateOfChildren(children, moduleList, module);
        }

    }

    static void generatePkg(@Nullable Children children, @NonNull List<Module> moduleList,
                                    @NonNull Module module, @NonNull String pTitle,
                                    @NonNull TiTleType pTiTleType) {
        if (!PKG.equals(pTiTleType)) {
            return;
        }
        String[] tokens = splitInto3Parts(pTitle);
        if (tokens.length > 1) {
            String pkg = tokens[1].trim();
            module.setPkg(pkg);
        }
        if (nonNull(children)) {
            generateOfChildren(children, moduleList, module);
        }
    }

}
