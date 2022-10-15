let bars = [];
let k = 0;
let l = 0;

function setup() {
  createCanvas(700, 500);
  bars = generateBars(100);
  frameRate(1)
}

function draw() {
  background(220);
  for (let i = 0; i < 100; i++) {
    rect(bars[i].x, bars[i].y + 500 - bars[i].h, bars[i].w, bars[i].h);
  }
  console.log(k, l);
  swap(l);

  if (l < 99) {
    l++;
  }
  if (l == 99) {
    k++;
    l = 0;
  }

  if (k > 99) {
    noLoop();
  }
}

function generateBars(n) {
  let numberSet = [];
  x = 0;
  for (i = 0; i < n; i++) {
    numberSet.push(new Bar(x, 0, 7, floor(random(0, 400))));
    x += 7;
  }
  return numberSet;
}

class Bar {
  constructor(x, y, w, h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
}

function swap(b) {
  if (bars[b].h < bars[b + 1].h) {
    let t = bars[b];
    bars[b] = bars[b + 1];
    bars[b + 1] = t;
    bars[b].x = b * 2;
    if (b > 1) bars[b - 1].x = b - 1 * 7;
    console.log(bars[b])
  }
}
