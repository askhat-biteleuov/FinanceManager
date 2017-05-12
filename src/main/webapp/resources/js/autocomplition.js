$(document).ready(function () {

    function selectHashtag (hashtag) {
        $('.hashtagSearchInput').val(hashtag);
    };

    $('.hashtagSearchInput').on('keyup change', function () {
        $('.hashtagSearchMenu').remove();
        var hashtag = $(this).val();
        $.ajax({
            type: 'GET',
            contentType: 'application/json; charset=UTF-8',
            url: "/hashtag",
            data: ({"str": hashtag})
        }).done(function (data) {
            var dropdown='<ul class="dropdown-menu hashtagSearchMenu">';
            var i;
            for (i = 0; i < data.length; ++i) {
                dropdown+='<li><a href="#" class="hashtagSearchLink">'+data[i]+'</a></li>';
            }
            dropdown+='</ul>';
            $('.hashtagSearchInput').after(dropdown);
            $('.hashtagSearchInput').dropdown('toggle');
        }).fail(function (error) {
        });
    });



    $('body').on("focus click",".hashtagSearchLink",function () {
        $('.hashtagSearchInput').val($(this).text());
    });
});
