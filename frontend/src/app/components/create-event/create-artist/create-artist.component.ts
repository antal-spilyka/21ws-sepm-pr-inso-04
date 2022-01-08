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
import { AddArtistDialogComponent } from './add-artist-dialog/add-artist-dialog.component';
import { MatDialog } from '@angular/material/dialog';

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
  canSaveEvent = false;
  performances: Performance[] = [];
  now = new Date().toISOString().split(':');

  form = this.formBuilder.group({
    name: [null, Validators.required],
    duration: [null, Validators.required],
    artistName: [null, Validators.required],
    artistDescription: null,
    hallName: [null, Validators.required],
    startTime: [this.now[0] + ':' + this.now[1], Validators.required],
    priceMultiplicant: [null, Validators.required]
  });

  constructor(
    private formBuilder: FormBuilder,
    private artistService: ArtistService,
    private hallService: HallService,
    private performanceService: PerformanceService,
    private dialog: MatDialog
  ) {
    this.artists = this.form.get('artistName').valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      switchMap(name => this.artistService.findArtist(name))
    );
    this.halls = this.form.get('hallName').valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      switchMap(name => this.hallService.findHall(name)
      )
    );
    this.form.controls.artistDescription.disable();
  }

  ngOnInit(): void {
  }

  clearForm() {
    this.formDirective.resetForm();
    this.form.reset();
    this.form.controls.startTime.setValue(this.now[0] + ':' + this.now[1]);
  }

  async addPerformance(formDirective: FormGroupDirective) {
    this.formDirective = formDirective;
    if (this.form.invalid) {
      this.setErrorFlag('Please fill out the form.');
      return;
    }
    if(new Date() > new Date(this.form.value.startTime)) {
      this.setErrorFlag('The start time must be in the future');
      return;
    }
    if(((!this.selectedArtist || this.selectedArtist.bandName !== this.form.value.artistName) && !this.isNewArtist) ||
        !this.form.value.artistName ) {
      this.setErrorFlag('Please select an Artist from the Dropdown Menu or create a new one.');
      return;
    }
    if(!this.selectedHall || ( this.selectedHall.name !== this.form.value.hallName)) {
      this.setErrorFlag('Please select a Hall from the Dropdown Menu.');
      return;
    }
    await this.submitArtistChanges();
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
    this.isNewArtist = false;
  }

  async submitArtistChanges() {
    console.log(this.selectedArtist);
    if (!this.isNewArtist && !this.selectedArtist) {
        this.setErrorFlag('Choose an artist for your performance');
        return;
    }

    this.artistService.createArtist(this.selectedArtist).subscribe({
      next: async next => {
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
      priceMultiplicant: this.form.value.priceMultiplicant
    } as Performance;

    this.performanceService.savePerformace(performance).subscribe({
      next: next => {
        this.performances.push(next);
        this.canSaveEvent = true;
      },
      error: error => {
        if(error.status === 409) {
          this.setErrorFlag('A Performance with the same name for this event already exists.');
        } else {
          this.setErrorFlag('Could not save Performance. (unknown error)');
        }
      },
      complete: () => {
        this.clearForm();
      }
    });
  }

  openNewArtistDialog(event: any) {
    event.preventDefault();
    const dialogRef = this.dialog.open(AddArtistDialogComponent);
    dialogRef.afterClosed().subscribe(
      artist => {
        if(artist.bandName && artist.description) {
          this.form.controls.artistName.setValue(artist.bandName);
          this.form.controls.artistDescription.setValue(artist.description);
          this.selectedArtist = artist;
          this.isNewArtist = true;
        }
      }
    );
  }

  async addEvent() {
    history.back();
  }
}
