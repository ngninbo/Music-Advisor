class Clock {

    int hours = 12;
    int minutes = 0;

    void next() {
        // implement me
        int nextMin = minutes + 1;
         if (nextMin > 59) {
             minutes = 0;
             int nextHour = hours + 1;
             if (nextHour > 12) {
                 hours = 1;
             } else {
                 hours = nextHour;
             }
         } else {
             minutes = nextMin;
         }
    }
}