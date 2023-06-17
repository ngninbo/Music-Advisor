class Clock {

    int hours = 12;
    int minutes = 0;

    void next() {
        // implement me
        int nextMin = minutes + 1;
         if (nextMin > 59) {
             minutes = 0;
             int nextHour = hours + 1;
             hours = nextHour > 12 ? 1 : nextHour;
         } else {
             minutes = nextMin;
         }
    }
}
