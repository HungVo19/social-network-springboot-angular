import {Component} from '@angular/core';
import {PostService} from "../../service/post/post.service";
import {TokenUtils} from "../shared/utils/token.utils";
import {ProfileService} from "../../service/profile/profile.service";
import {FormControl, FormGroup} from "@angular/forms";
import Swal from "sweetalert2";
import {SweetAlertService} from "../../service/alert/sweet-alert.service";
import * as events from "events";

@Component({
  selector: 'app-status-post',
  templateUrl: './status-post.component.html',
  styleUrls: ['./status-post.component.css']
})
export class StatusPostComponent {
  userId!: any;
  statusForm!: any;
  user!: any;
  files = [];

  constructor(private postService: PostService,
              private userService: ProfileService,
              private alertService: SweetAlertService) {
  }

  ngOnInit() {
    const token = sessionStorage.getItem('access_token');
    if (token) {
      const payload = TokenUtils.parseJwt(token);
      this.userId = payload.id;
    }
    this.getCurrentUser(this.userId);
    this.initializeStatusForm();
  }

  getCurrentUser(id: any) {
    this.userService.getCurrentUser(id).subscribe({
      next: (data: any) => {
        this.user = data.data;
      }
    })
  }

  initializeStatusForm() {
    this.statusForm = new FormGroup({
      content: new FormControl()
    })
  }

  submitStatus() {
    const postCreate = {
      content: this.statusForm.controls[('content')].value,
      privacy: 'PUBLIC'
    }

    this.postService.createNewPost(this.userId, postCreate, this.files).subscribe({
      next: () => {
        this.statusForm.reset();
        this.alertService.success("New status created")
      },
      error: (data: any) => {
        const text = data.error.error;
        this.alertService.error("Error",text);
      }
    })
  }

  onFilesSelected(event: any) {
    this.files = event.target.files
  }
}
