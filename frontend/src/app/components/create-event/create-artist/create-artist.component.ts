import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {debounceTime, distinctUntilChanged, Observable, switchMap} from 'rxjs';
import {Artist} from 'src/app/dtos/artist';
import { EventDto } from 'src/app/dtos/eventDto';
import {ArtistService} from 'src/app/services/artist.service';
import {Hall} from '../../../dtos/hall';
import {HallService} from '../../../services/hall.service';
import {Performance} from '../../../dtos/Performance';
import {EventService} from '../../../services/event.service';

@Component({
  selector: 'app-create-artist',
  templateUrl: './create-artist.component.html',
  styleUrls: ['./create-artist.component.scss']
})
export class CreateArtistComponent implements OnInit {

  @Input() event: EventDto;
  @Input() setErrorFlag: (message?: string) => void;

  artists: Observable<Artist[]>;
  selectedArtist: Artist;
  isNewArtist = false;
  halls: Observable<Hall[]>;
  selectedHall: Hall;
  isNewHall = false;
  canSaveEvent = true;

  form = this.formBuilder.group({
    name: [null, Validators.required],
    duration: [null, Validators.required],
    artistName: [null, Validators.required],
    artistDescription: [null, Validators.required],
    hallName: [null, Validators.required]
  });

  constructor(
    private formBuilder: FormBuilder,
    private artistService: ArtistService,
    private hallService: HallService,
    private eventService: EventService
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
    console.log(this.event);
  }

  handleNewArtist() {
    this.isNewArtist = !this.isNewArtist;
    if (this.isNewArtist) {
      this.selectedArtist = null;
      this.form.controls.artistName.setValue(null);
      this.form.controls.artistDescription.setValue(null);
    }
  }

  async addPerformance() {
    if (!this.form.valid) {
      return; // todo hier vielleicht einfach ne nachricht anzeigen lassen.
    }
    await this.submitHallChanges();
    await this.submitArtistChanges();
    const performance: Performance = { name: this.form.value.name, duration: this.form.value.duration,
      artist: this.selectedArtist, hall: this.selectedHall};
    this.event.performances.push(performance);
    console.log(this.event);
    this.canSaveEvent = false;
  }

  onSelectHall(hall: Hall) {
    const {name} = hall;
    this.selectedHall = hall;
    this.form.controls.hallName.setValue(name);
  }

  onSelectArtist(artist: Artist) {
    const {bandName, description} = artist;
    this.selectedArtist = artist;
    this.form.controls.artistName.setValue(bandName);
    this.form.controls.artistDescription.setValue(description);
  }

  async submitHallChanges() {
    if (this.isNewHall) {
      this.selectedHall = { name: this.form.value.hallName, eventPlaceDto: this.event.eventPlace } as Hall;
    } else {
      if (!this.selectedHall) {
        this.setErrorFlag('Choose a hall for your performance');
        return;
      }
    }
    console.log(this.selectedHall);
    this.hallService.saveHall(this.selectedHall).subscribe({
      next: async hall => {
        this.selectedHall =  hall;
        console.log(hall);
      },
      error: error => {
        if(error.status === 409) {
          this.setErrorFlag('An hall with the same name already exists.');
        } else {
          this.setErrorFlag();
        }
      }
    });
  }

  async submitArtistChanges() {
    if (this.isNewArtist) {
      this.selectedArtist = { bandName: this.form.value.artistName, description: this.form.value.artistDescription } as Artist;
    } else {
      if (!this.selectedArtist) {
        this.setErrorFlag('Choose an artist for your performance');
        return;
      }
    }
    console.log(this.selectedArtist);
    this.artistService.createArtist(this.selectedArtist).subscribe({
      next: async next => {
        console.log(next);
        this.selectedArtist = next;
      },
        error: error => {
          if(error.status === 409) {
            this.setErrorFlag('An artist with the same name already exists.');
          } else {
            this.setErrorFlag();
          }
      }
    });
  }

  async addEvent() {
    if (!this.form.valid) {
      return; // todo hier vielleicht einfach ne nachricht anzeigen lassen.
    }
    this.eventService.saveEvent(this.event).subscribe((event: EventDto) => {
      console.log(event);
      this.event = event;
    });
      history.back();
  }
}
