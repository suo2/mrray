package www.mrray.cn.model

/**
 *@author suo
 *@date 2018/11/8
 *@desc:
 */
data class PageBaseModel<T>(
        val content: List<T>,
        val currentPageElements: Int,
        val direction: String,
        val firstPage: Boolean,
        val lastPage: Boolean,
        val nextPage: Int,
        val page: Int,
        val prevPage: Int,
        val `property`: String,
        val size: Int,
        val totalElements: Int,
        val totalPage: Int
)