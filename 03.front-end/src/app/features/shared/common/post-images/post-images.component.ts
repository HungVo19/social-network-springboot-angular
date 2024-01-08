import {Component, Input} from '@angular/core';
import {BeforeSlideDetail} from "lightgallery/lg-events";
import lgZoom from 'lightgallery/plugins/zoom';


@Component({
  selector: 'app-post-images',
  templateUrl: './post-images.component.html',
  styleUrls: ['./post-images.component.css']
})
export class PostImagesComponent {
  @Input() images!: any;

  settings = {
    counter: false,
    thumbnail: true,
    animateThumb: false,
    showThumbByDefault: false,
    plugins: [lgZoom]
  };

  gallery = {
    // Gallery settings like plugins if any
  };

  onBeforeSlide = (detail: BeforeSlideDetail): void => {
    const {index, prevIndex} = detail;
    console.log(index, prevIndex);
  };
}
