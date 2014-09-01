/**
 * Created by ru-d on 7/11/14.
 */

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
