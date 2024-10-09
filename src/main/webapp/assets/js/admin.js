function toggleAside() {
    var aside = document.getElementById("myAside");
    var righttt = document.getElementById("right");
    var rightttBtn = document.getElementById("rightBtn");
    var leftBtn = document.getElementById("leftBtn");
    var links = document.querySelectorAll(".link");

    if (aside.style.width === "5%") {
        aside.style.width = "17%";
        righttt.style.width="83%";
        leftBtn.style.display="block";
        rightttBtn.style.display="none";
        links.forEach(function (link) {
            link.style.display = "block";
        });

    } else {
        aside.style.width = "5%";
        righttt.style.width="95%";
        leftBtn.style.display="none";
        rightttBtn.style.display="block";

        links.forEach(function (link) {
            link.style.display = "none";
        });
    }
}
  