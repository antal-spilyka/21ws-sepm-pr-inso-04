import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroupDirective, Validators} from '@angular/forms';
import {debounceTime, distinctUntilChanged, Observable, switchMap} from 'rxjs';
import {Artist} from 'src/app/dtos/artist';
import { EventDto } from 'src/app/dtos/eventDto';
import {ArtistService} from 'src/app/services/artist.service';
import {Hall} from '../../../dtos/hall';
import {HallService} from '../../../services/hall.service';
import {Performance} from '../../../dtos/performance';
import { PerformanceService } from 'src/app/services/performance.service';

@Component({
  selector: 'app-create-artist',
  templateUrl: './create-artist.component.html',
  styleUrls: ['./create-artist.component.scss']
})
export class CreateArtistComponent implements OnInit {

  @Input() event: EventDto;
  @Input() setErrorFlag: (message?: string) => void;
  formDirective: FormGroupDirective;

  artists: Observable<Artist[]>;
  selectedArtist: Artist;
  isNewArtist = false;
  halls: Observable<Hall[]>;
  selectedHall: Hall;
  isNewHall = false;
  canSaveEvent = false;
  performances: Performance[] = [];
  now = new Date().toISOString().split(':');

  form = this.formBuilder.group({
    name: [null, Validators.required],
    duration: [null, Validators.required],
    artistName: [null, Validators.required],
    artistDescription: [null, Validators.required],
    hallName: [null, Validators.required],
    startTime: [this.now[0] + ':' + this.now[1], Validators.required]
  });

  constructor(
    private formBuilder: FormBuilder,
    private artistService: ArtistService,
    private hallService: HallService,
    private performanceService: PerformanceService
  ) {
    this.artists = this.form.get('artistName').valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      switchMap(name => !this.isNewArtist ?
        this.artistService.findArtist(name) :
        new Observable<Artist[]>()
      )
    );
    this.halls = this.form.get('hallName').valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      switchMap(name => !this.isNewHall ?
        this.hallService.findHall(name) : new Observable<Hall[]>()
      )
    );
  }

  handleNewHall() {
    this.isNewHall = !this.isNewHall;
    if (this.isNewHall) {
      this.selectedHall = null;
      this.form.controls.hallName.setValue(null);
    }
  }

  ngOnInit(): void {
  }

  handleNewArtist() {
    this.isNewArtist = !this.isNewArtist;
    if (this.isNewArtist) {
      this.selectedArtist = null;
      this.form.controls.artistName.setValue(null);
      this.form.controls.artistDescription.setValue(null);
    }
  }

  clearForm() {
    this.formDirective.resetForm();
    this.form.reset();
    this.form.controls.startTime.setValue(this.now[0] + ':' + this.now[1]);
  }

  async addPerformance(formDirective: FormGroupDirective) {
    console.log(formDirective);
    this.formDirective = formDirective;
    if (!this.form.valid) {
      this.setErrorFlag('Please fill out the form.');
      return;
    }
    if(new Date() > new Date(this.form.value.startTime)) {
      this.setErrorFlag('The start time must be in the future');
      return;
    }
    if(!this.selectedArtist || ( this.selectedArtist.bandName !== this.form.value.artistName && this.isNewArtist )) {
      this.setErrorFlag('Please select an Artist from the Dropdown Menu or create a new one.');
      return;
    }
    if(!this.selectedHall || ( this.selectedHall.name !== this.form.value.hallName && this.isNewHall )) {
      this.setErrorFlag('Please select a Hall from the Dropdown Menu or create a new one.');
      return;
    }
    await this.submitHallChanges();
  }

  onSelectHall(hall: Hall) {
    const { name } = hall;
    this.selectedHall = hall;
    this.form.controls.hallName.setValue(name);
  }

  onSelectArtist(artist: Artist) {
    const { bandName, description } = artist;
    this.selectedArtist = artist;
    this.form.controls.artistName.setValue(bandName);
    this.form.controls.artistDescription.setValue(description);
  }

  async submitHallChanges() {
    if (this.isNewHall) {
      this.selectedHall = {
        name: this.form.value.hallName,
        eventPlaceDto: this.event.eventPlace
      } as Hall;
    } else {
      if (!this.selectedHall) {
        this.setErrorFlag('Choose a hall for your performance');
        await this.submitArtistChanges();
        return;
      }
    }
    this.hallService.saveHall(this.selectedHall).subscribe({
      next: async hall => {
        this.selectedHall =  hall;
        console.log(hall);
      },
      error: error => {
        if(error.status === 409) {
          this.setErrorFlag('A hall with the same name already exists.');
        } else {
          this.setErrorFlag('Could not save Hall. (unknown error)');
        }
      },
      complete: async () => {
        await this.submitArtistChanges();
      }
    });
  }

  async submitArtistChanges() {
    if (this.isNewArtist) {
      this.selectedArtist = {
        bandName: this.form.value.artistName,
        description: this.form.value.artistDescription
      } as Artist;
    } else {
      if (!this.selectedArtist) {
        this.setErrorFlag('Choose an artist for your performance');
        return;
      }
    }
    this.artistService.createArtist(this.selectedArtist).subscribe({
      next: async next => {
        console.log(next);
        this.selectedArtist = next;
      },
      error: error => {
        if(error.status === 409) {
          this.setErrorFlag('An artist with the same name already exists.');
        } else {
          this.setErrorFlag('Could not save Artist. (unknown error)');
        }
      },
      complete: async () => {
        await this.submitPerformance();
      }
    });
  }

  async submitPerformance() {
    const performance = {
      name: this.form.value.name,
      startTime: this.form.value.startTime,
      duration: this.form.value.duration,
      artist: this.selectedArtist,
      hall: this.selectedHall,
      eventDto: this.event,
    } as Performance;

    this.performanceService.savePerformace(performance).subscribe({
      next: next => {
        this.performances.push(next);
        this.canSaveEvent = true;
      },
      error: error => {
        if(error.status === 409) {
          this.setErrorFlag('An artist with the same name already exists.');
        } else {
          this.setErrorFlag('Could not save Performance. (unknown error)');
        }
      },
      complete: () => {
        this.clearForm();
      }
    });
  }

  async addEvent() {
    history.back();
  }
}
