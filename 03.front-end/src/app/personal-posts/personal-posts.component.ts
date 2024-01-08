import {Component, OnInit} from '@angular/core';
import {PostService} from "../service/post/post.service";
import {ProfileService} from "../service/profile/profile.service";
import {TokenUtils} from "../features/shared/utils/token.utils";
import Swal from "sweetalert2";
import {FormControl, FormGroup} from "@angular/forms";
import {PostCommentService} from "../service/comment/post-comment.service";
import {WebsocketService} from "../service/websocket/websocket.service";
import {SweetAlertService} from "../service/alert/sweet-alert.service";


@Component({
  selector: 'app-personal-posts',
  templateUrl: './personal-posts.component.html',
  styleUrls: ['./personal-posts.component.css']
})
export class PersonalPostsComponent implements OnInit {
  posts!: any;
  userId!: any;
  commentForm!: any;
  user!: any;

  constructor(private postService: PostService,
              private userService: ProfileService,
              private postCommentService: PostCommentService,
              private websocketService: WebsocketService,
              private userProfileService: ProfileService,
              private alertService: SweetAlertService) {
  }

  ngOnInit(): void {
    const token = sessionStorage.getItem('access_token');
    if (token) {
      const payload = TokenUtils.parseJwt(token);
      this.userId = payload.id;
      this.user = this.getCurrentUser(this.userId);
      this.getPosts();
      this.connectWebSocket();
    }
    this.initializeCommentForm();
  }

  getPosts() {
    this.postService.getUserPosts(this.userId).subscribe({
      next: (data: any) => {
        this.posts = data.data.content;
      },
      error: (data: any) => {
        const text = JSON.stringify(data.error.error)
        this.alertService.error("Error", text);
      }
    })
  }

  initializeCommentForm() {
    this.commentForm = new FormGroup({
      content: new FormControl()
    })
  }

  submitComment(userId: any, postId: any) {
    const comment = this.commentForm.value;
    this.postCommentService.postComment(userId, postId, comment).subscribe({
      next: () => {
        this.commentForm.reset();
        this.getPosts();
      },
      error: (data: any) => {
        const text = JSON.stringify(data.error.error)
        this.alertService.error("Error", text);
      }
    })
  }

  private getCurrentUser(userId: any) {
    this.userProfileService.getCurrentUser(userId).subscribe({
      next: (data: any) => {
        this.user = data.data;
      },
      error: (data: any) => {
        const text = JSON.stringify(data.error.error)
        this.alertService.error("Error", text);
      }
    })
  }

  private connectWebSocket() {
    this.getNewComment();
    this.gotNewLike();
  }

  private getNewComment() {
    const topic = `/topic/user.${this.userId}.newComment`
    this.websocketService.subscribeToTopic(topic, (message) => {
      this.getPosts();
    }).then(() => {
    })
  }

  private gotNewLike() {
    const topic = `/topic/user.${this.userId}.newPostLike`;
    this.websocketService.subscribeToTopic(topic, (message) => {
      this.getPosts();
    }).then(() => {
    })
  }
}
