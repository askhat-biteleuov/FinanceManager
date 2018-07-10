$(document).ready(function () {

    $('.hashtagSearchInput').on('keyup click', function () {
        var hashtags = ($(this).val()).split(" ");
        var hashtag = hashtags[hashtags.length-1];
        $('.hashtagSearchInput').dropdown('toggle');
        $.ajax({
            type: 'GET',
            contentType: 'application/json; charset=UTF-8',
            url: "/hashtag",
            data: ({"str": hashtag})
        }).done(function (data) {
            $('.hashtagSearchMenu').remove();
            var dropdown='<ul class="dropdown-menu scrollable-menu hashtagSearchMenu">';
            var i;
            for (i = 0; i < data.length; ++i) {
                dropdown+='<li class="hashtagSearchLink"><a>'+data[i]+'</a></li>';
            }
            dropdown+='</ul>';
            $('.hashtagSearchInput').after(dropdown);
            $('.hashtagSearchInput').dropdown('toggle');
        }).fail(function (error) {
            $('.hashtagSearchMenu').remove();
        });
    });

    $('body').on("click",".hashtagSearchLink",function () {
        var hashtags = ($('.hashtagSearchInput').val()).split(" ");
        var oldHashtags = hashtags.slice(0,hashtags.length-1);
        $('.hashtagSearchInput').val(oldHashtags.join(" ")+" "+$(this).text());
        $('.hashtagSearchMenu').remove();
    });
});
