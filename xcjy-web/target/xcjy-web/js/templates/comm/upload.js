/**
 * 动态构建img的src路径
 * 
 * @param filePath
 * @returns
 */
function buildImgSrc(filePath) {
	if (!filePath) {
		return '';
	}
	return 'download?filePath='+basePath + filePath;
}
