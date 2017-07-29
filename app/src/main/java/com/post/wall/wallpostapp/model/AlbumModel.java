package com.post.wall.wallpostapp.model;

public class AlbumModel {
	public int albumId;
	public int count;
	public String title;
	public String description;
	public String category;
	public int categoryId;
	public int viewCount;
	public String date;
	public int subjectId;
	public String ownerType;
	public String username;
	public String url;
	public String userphoto;
	public int user_id;
	int search;
	String auth_view;
	String auth_comment;
	String auth_tag;
	int view_count, points, friends_count, commentCount, likeCount;
	boolean islike;
	boolean canComment;
	String likeString;
	int cansharable;
	boolean canView;

	// public List<File> files;
	//
	//
	// public List<File> getFiles() {
	// return files;
	// }
	//
	// public void setFiles(List<File> files) {
	// this.files = files;
	// }

	public boolean isCanComment() {
		return canComment;
	}

	public String getLikeString() {
		return likeString;
	}

	public void setLikeString(String likeString) {
		this.likeString = likeString;
	}

	public void setCanComment(boolean canComment) {
		this.canComment = canComment;
	}

	public int getCansharable() {
		return cansharable;
	}

	public void setCansharable(int cansharable) {
		this.cansharable = cansharable;
	}

	public boolean isIslike() {
		return islike;
	}

	public void setIslike(boolean islike) {
		this.islike = islike;
	}

	public int getUser_id() {
		return user_id;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getSearch() {
		return search;
	}

	public void setSearch(int search) {
		this.search = search;
	}

//	public String getPrivacy() {
//		return auth_view;
//	}
//
//	public void setPrivacy(String privacy) {
//		this.auth_view = privacy;
//	}
//
//	public String getCommentprivacy() {
//		return auth_comment;
//	}
//
//	public void setCommentprivacy(String commentprivacy) {
//		this.auth_comment = commentprivacy;
//	}
//
//	public String getTagging() {
//		return auth_tag;
//	}
//
//	public void setTagging(String tagging) {
//		this.auth_tag = tagging;
//	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserphoto() {
		return userphoto;
	}

	public void setUserphoto(String userphoto) {
		this.userphoto = userphoto;
	}

	public String getAuth_view() {
		return auth_view;
	}

	public void setAuth_view(String auth_view) {
		this.auth_view = auth_view;
	}

	public String getAuth_comment() {
		return auth_comment;
	}

	public void setAuth_comment(String auth_comment) {
		this.auth_comment = auth_comment;
	}

	public String getAuth_tag() {
		return auth_tag;
	}

	public void setAuth_tag(String auth_tag) {
		this.auth_tag = auth_tag;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public boolean isCanView() {
		return canView;
	}

	public void setCanView(boolean canView) {
		this.canView = canView;
	}

}
