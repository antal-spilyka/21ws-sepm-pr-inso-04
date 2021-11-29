import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Artist } from 'src/app/dtos/artist';
import { Category } from 'src/app/dtos/category';
import { EventInquiry } from 'src/app/dtos/eventInquiry';
import { Room } from 'src/app/dtos/room';
import { EventService } from 'src/app/services/event.service';

@Component({
  selector: 'app-create-actual-event',
  templateUrl: './create-actual-event.component.html',
  styleUrls: ['./create-actual-event.component.scss']
})
export class CreateActualEventComponent implements OnInit {

  @Input() artist: Artist;
  @Input() room: Room;
  @Input() category: Category;
  @Input() setErrorFlag: () => void;

  disableTyping = true;
  now = new Date().toISOString().split(':');
  today = new Date();

  form = this.formBuilder.group({
    name: [null, Validators.required],
    duration: [null, Validators.required],
    content: [null, Validators.required],
    dateTime: [this.now[0] + ':' + this.now[1], Validators.required]
  });

  constructor(
    private formBuilder: FormBuilder,
    private eventService: EventService,
    private router: Router) { }

  ngOnInit(): void {
  }

  debugPrint() {
    console.log(this.form.controls.dateTime.value);
  }

  async nextStep() {
    if(!this.form.valid) {
      return;
    }
    if(this.artist && this.room && this.category) {
      const eventInquiry = new EventInquiry();
      eventInquiry.name = this.form.value.name;
      eventInquiry.content = this.form.value.content;
      eventInquiry.dateTime = new Date(this.form.value.dateTime);
      eventInquiry.duration = this.form.value.duration;
      eventInquiry.roomId = this.room.id;
      eventInquiry.artistId = this.artist.id;
      eventInquiry.categoryName = this.category.name;
      console.log(eventInquiry);
      this.eventService.createEvent(eventInquiry).subscribe({
        next: next => {
          this.router.navigateByUrl('/');
        },
        error: error => {
          this.setErrorFlag();
        }
      });
    }

  }

}
