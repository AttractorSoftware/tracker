/**
 * Created by ru-d on 7/11/14.
 */
jQuery(document).ready(function () {
    group_activity_feed();
});

function group_activity_feed(){
    var feed = $('.feed');

    for(var row = 0; row < feed.length; row ++){
        var amount = 1;

        for(var i = 1; i < feed[row].children.length;){

            if(feed[row].children[i].childElementCount != 0){
                var nextSlot = i + 1;

                while(nextSlot < feed[row].children.length && feed[row].children[nextSlot].childElementCount != 0 &&
                      feed[row].children[i].innerText == feed[row].children[nextSlot].innerText){
                    amount ++;
                    feed[row].deleteCell(nextSlot);
                }
                feed[row].children[i].colSpan = amount;
                i ++;
            }
            else{
                i ++;
            }
        }
    }
    highlight_activity_feed();
}

function highlight_activity_feed(){
    var grouped_feed = $('.feed #activity-feed');
    var step = 1;

    for (var slot = 0; slot < grouped_feed.length;){
        var nextSlot = slot+1;

        while(nextSlot < grouped_feed.length && grouped_feed[slot].innerText == grouped_feed[nextSlot].innerText){
            nextSlot ++;
        }
        slot = nextSlot;
        nextSlot = slot+1;

        if(slot < grouped_feed.length){
            grouped_feed[slot].style.backgroundColor = "rgb(44, 81, 99)";

            while(nextSlot<grouped_feed.length && grouped_feed[slot].innerText == grouped_feed[nextSlot].innerText){
                grouped_feed[nextSlot].style.backgroundColor = "rgb(44, 81, 99)";
                nextSlot++;
                step ++;
            }
        }
        slot += step;
    }
}
