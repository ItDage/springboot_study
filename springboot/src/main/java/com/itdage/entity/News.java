package com.itdage.entity;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @ClassName: News
 * @Description: 新闻接口返回字段
 * @author: scy
 * @date: 2018年10月20日 上午11:11:45
 */
@Component
public class News {
	/**
	 * 返回的消息 -- 是否成功
	 */
	private String reason;
	/**
	 * 返回的结果
	 */
	private Result result;

	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "News [reason=" + reason + ", result=" + result + "]";
	}
}

@Component
class Result {
	/**
	 * 返回的状态
	 */
	private String stat;
	private List<Data> data;

	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public List<Data> getData() {
		return data;
	}
	public void setData(List<Data> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Result [stat=" + stat + ", data=" + data + "]";
	}
}

@Component
class Data {
	private String uniquekey;
	private String title;
	private String data;
	private String category;
	private String author_name;
	private String url;
	private String thumbnail_pic_s;
	private String thumbnail_pic_s02;
	private String thumbnail_pic_s03;

	@Override
	public String toString() {
		return "Data [uniquekey=" + uniquekey + ", title=" + title + ", data=" + data + ", category=" + category
				+ ", author_name=" + author_name + ", url=" + url + ", thumbnail_pic_s=" + thumbnail_pic_s
				+ ", thumbnail_pic_s02=" + thumbnail_pic_s02 + ", thumbnail_pic_s03=" + thumbnail_pic_s03 + "]";
	}
	public String getUniquekey() {
		return uniquekey;
	}
	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbnail_pic_s() {
		return thumbnail_pic_s;
	}
	public void setThumbnail_pic_s(String thumbnail_pic_s) {
		this.thumbnail_pic_s = thumbnail_pic_s;
	}
	public String getThumbnail_pic_s02() {
		return thumbnail_pic_s02;
	}
	public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
		this.thumbnail_pic_s02 = thumbnail_pic_s02;
	}
	public String getThumbnail_pic_s03() {
		return thumbnail_pic_s03;
	}
	public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
		this.thumbnail_pic_s03 = thumbnail_pic_s03;
	}
}
