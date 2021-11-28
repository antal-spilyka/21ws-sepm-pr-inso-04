import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-create-actual-event',
  templateUrl: './create-actual-event.component.html',
  styleUrls: ['./create-actual-event.component.scss']
})
export class CreateActualEventComponent implements OnInit {

  disableTyping = true;
  now = new Date().toISOString().split(':');
  today = new Date();

  form = this.formBuilder.group({
    name: [null],
    duration: [null],
    content: [null],
    dateTime: [this.now[0] + ':' + this.now[1]]
  });

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  debugPrint() {
    console.log(this.form.controls.dateTime.value);
  }

}
