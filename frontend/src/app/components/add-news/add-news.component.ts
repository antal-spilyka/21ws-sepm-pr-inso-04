import {Component, OnInit} from '@angular/core';
import {debounceTime, distinctUntilChanged, interval, Observable, switchMap} from 'rxjs';
import {EventService} from '../../services/event.service';
import {EventDto} from '../../dtos/eventDto';
import {FormBuilder, Validators} from '@angular/forms';
import {News} from '../../dtos/news';
import {NewsService} from '../../services/news.service';
import { FileService } from 'src/app/services/file.service';
import { Picture } from 'src/app/dtos/picture';

@Component({
  selector: 'app-add-news',
  templateUrl: './add-news.component.html',
  styleUrls: ['./add-news.component.scss']
})
export class AddNewsComponent implements OnInit {
  error = false;
  errorMessage = '';
  events: Observable<EventDto[]>;
  currentEvent: EventDto;
  images: Array<object> = [
    {
      image: '../../../assets/noImage.jpg',
      thumbImage: '../../../assets/noImage.jpg'
    }
  ];
  filesToUpload = [];
  imageUrl;
  filesUploaded: Picture[] = [];

  form = this.formBuilder.group({
    id: ['',],
    chosenEvent: ['', Validators.required],
    rating: [null, Validators.required],
    fsk: [18, Validators.required],
    shortDescription: ['', [Validators.required, Validators.maxLength(255)]],
    longDescription: ['', [Validators.required, Validators.maxLength(1000)]]
  });

  constructor(
    private eventService: EventService,
    private formBuilder: FormBuilder,
    private newsService: NewsService,
    private fileService: FileService
  ) {
    this.events = this.form.get('chosenEvent').valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      switchMap(name => this.eventService.findEventByName(name))
    );
  }

  ngOnInit() {
  }

  setChosenEvent(event: EventDto) {
    this.currentEvent = event;
  }

  goBack() {
    history.back();
  }

  setRating(rating: number) {
    this.form.controls.rating.setValue(rating);
  }

  getErrorMessage(control) {
    if (control.hasError('required')) {
      return 'You must enter a value';
    }
    if (control.hasError('maxlength')) {
      return 'The description is too long';
    }
    return '';
  }

  save() {
    if (this.form.invalid) {
      this.setErrorFlag('Please fill out the form');
      return;
    }
    const newsRequest = {
      event: this.currentEvent,
      rating: this.form.controls.rating.value,
      fsk: this.form.controls.fsk.value,
      shortDescription: this.form.controls.shortDescription.value,
      longDescription: this.form.controls.longDescription.value,
      createDate: new Date(),
      pictures: this.filesUploaded.length === 0 ? null : this.filesUploaded
    } as News;
    console.log(newsRequest);
    this.newsService.save(newsRequest).subscribe({
      error: error => {
        this.setErrorFlag();
      },
      complete: () => {
        history.back();
      }
    });
  }

  handleFileInput(files: FileList) {
    const file = files.item(0);
    this.filesToUpload.push(file);
    if(this.filesToUpload.length === 0) {
      this.images = [];
    }
    const reader = new FileReader();
    reader.onload = (event: any) => {
      this.imageUrl = event.target.result;
      this.images.push(
        {
          image: this.imageUrl,
          thumbImage: this.imageUrl
        }
      );
    };
    this.fileService.saveFile(file).subscribe({
      next: next => {
        this.filesUploaded.push({
          url: next.uri
        });
        console.log(this.filesUploaded);
        reader.readAsDataURL(this.filesToUpload[this.filesToUpload.length - 1]);
      },
      error: error => {
        this.setErrorFlag('Could not upload image (max size: 1 MB)');
      }
    });
  }

  vanishError() {
    this.error = false;
    this.errorMessage = '';
  }

  setErrorFlag(message?: string) {
    this.errorMessage = message ? message : 'Unknown Error';
    this.error = true;
  }
}
