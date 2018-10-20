function loadNews(){
	$.ajax({
		url: window.localStorage.project + '/loadNews',
		type: "POST",
		dataType: "json",
		success: function(result){
			$("#news").empty();
			var newsJsonObject = result.result;
			for(var i = 0; i < newsJsonObject.data.length; i++){
				var title =  newsJsonObject.data[i].title;
				title = title.replace(/\s+/g, "")
				$("#news").append('<li style="margin-top:5px;"><a class="showBG" onclick=news(\''+ title +'\',"'+newsJsonObject.data[i].url+'")><div> <img src="'+newsJsonObject.data[i].thumbnail_pic_s+'"'
                        + "width='40px' height='40px'> &nbsp;&nbsp;&nbsp;&nbsp;"
						+ newsJsonObject.data[i].title
						+ '</p></div></a></li>');
			}
		},
		error: function(result){
			
		}
	})
}