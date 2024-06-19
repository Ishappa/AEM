let currentIndex = 0;
const intervalTime = 5000;
let touchStartX = 0;
let touchEndX = 0;
let autoplayTimeout;

console.log("hello");

function showSlide(index) {
  const carousel = document.querySelector('.carousel');
  const totalItems = document.querySelectorAll('.carousel-item').length;

  if (index >= totalItems) {
    index = 0;
  } else if (index < 0) {
    index = totalItems - 1;
  }

  currentIndex = index;

  const width = document.querySelector('.carousel-item').offsetWidth;
  const newTransformValue = -currentIndex * width + 'px';
  carousel.style.transform = 'translateX(' + newTransformValue + ')';

  updateDots(currentIndex);
}function showSlide(index) {
  const carousel = document.querySelector('.carousel');
  const totalItems = document.querySelectorAll('.carousel-item').length;

  if (index >= totalItems) {
    index = 0;
  } else if (index < 0) {
    index = totalItems - 1;
  }

  currentIndex = index;

  const width = document.querySelector('.carousel-item').offsetWidth;
  const newTransformValue = -currentIndex * width + 'px';
  carousel.style.transform = 'translateX(' + newTransformValue + ')';

  updateDots(currentIndex);
}


function prevSlide() {
  stopAutoPlay(); // Stop autoplay when manually navigating
  showSlide(currentIndex - 1);
  setTimeout(() => autoPlay(), 5000); // Resume autoplay after a delay
}

function nextSlide() {
  stopAutoPlay(); // Stop autoplay when manually navigating
  showSlide(currentIndex + 1);
  setTimeout(() => autoPlay(), 5000); // Resume autoplay after a delay
}


function handleTouchStart(event) {
  touchStartX = event.touches[0].clientX;
}

function handleTouchEnd(event) {
  touchEndX = event.changedTouches[0].clientX;
  handleSwipe();
}

function handleSwipe() {
  const swipeThreshold = 50;
  const swipeDifference = touchStartX - touchEndX;

  if (swipeDifference > swipeThreshold) {
    nextSlide();
  } else if (swipeDifference < -swipeThreshold) {
    prevSlide();
  }
}

function autoPlay() {
  autoplayTimeout = setTimeout(function () {
    nextSlide();
    autoPlay();
  }, intervalTime);
}

function stopAutoPlay() {
  clearTimeout(autoplayTimeout);
}

function createDots() {
  const totalItems = document.querySelectorAll('.carousel-item').length;
  const dotsContainer = document.getElementById('carouselDots');

  for (let i = 0; i < totalItems; i++) {
    const dot = document.createElement('div');
    dot.classList.add('dot');
    dot.setAttribute('data-index', i);
    dot.addEventListener('click', function () {
      stopAutoPlay(); // Stop autoplay when manual navigation occurs
      showSlide(i);
    });
    dotsContainer.appendChild(dot);
  }

  updateDots(currentIndex);
}

function updateDots(index) {
  const dots = document.querySelectorAll('.dot');
  dots.forEach((dot, i) => {
    dot.classList.toggle('active', i === index);
  });
}

document.querySelector('.carousel-container').addEventListener('touchstart', handleTouchStart);
document.querySelector('.carousel-container').addEventListener('touchend', handleTouchEnd);

createDots();
autoPlay();