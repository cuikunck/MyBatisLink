package com.netease.mybatislink;

public interface IBatisConstants {

	public final static String[] ibatisMethods = { "insert", "update", "delete", "queryForObject", "queryForList",
			"queryWithRowHandler", "queryForPaginatedList", "queryForMap", "selectOne", "selectList", "selectMap",
			"select" };
	public final static String[] mapAttributeNames = { "resultMap", "parameterMap", "parameterClass", "class",
			"parameterType" };

	public final static String[] ROOT_TAG = { "sqlMap", "mapper", "configuration" };
	public final static String[] STATEMENT_TAG = { "typeAlias", "cacheModel", "resultMap", "parameterMap", "sql",
			"statement", "insert", "update", "delete", "select", "procedure", "properties", "settings", "typeAliases",
			"typeHandlers", "objectFactory", "objectWrapperFactory", "plugins", "environments", "mappers",
			"resultObjectFactory", "transactionManager", "sqlMap" };

	public final static String TYPE_ALIAS_TAG = "typeAlias";
	public final static String ATTRIBUTE_ID = "id";
	public final static String ATTRIBUTE_NAMESPACE = "namespace";
	public final static String ATTRIBUTE_TYPE_ALIAS = "alias";

	public static final String TAG_END = ">";
	public static final String TAG_START = "<";

	public static final String LABEL_SEARCH_PATTERN = "Input the id of iBatis statement:";
	public static final String LABEL_TABLE_VIEWER = "Matches iBatis statement:";
	public static final String LABEL_FILTER_HISTORY_JOB = "Filtering history items";
	public static final String LABEL_FILTER_JOB = "Filtering items";
	public static final String DIALOG_TITLE = "Open iBatis Config";

	public static final String CONCAT_STRING = " - ";
}
