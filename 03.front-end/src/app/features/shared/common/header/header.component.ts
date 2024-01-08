import {Component} from '@angular/core';
import {TokenUtils} from "../../utils/token.utils";
import {ProfileService} from "../../../../service/profile/profile.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  userId!: any;
  user:any;

  constructor(private service:ProfileService,
              private router:Router) {

  }

  ngOnInit() {
    const token = sessionStorage.getItem('access_token');
    if(token) {
      const payload = TokenUtils.parseJwt(token);
      this.userId = payload.id;
      this.getCurrentUser(this.userId);
    }
  }


  getCurrentUser(id:any) {
    this.service.getCurrentUser(id).subscribe({
      next:(data:any) =>{
        this.user = data.data;
      }
    })
  }

  logOut() {
    sessionStorage.removeItem("access_token");
    this.router.navigate(["/home"])
  }
}
