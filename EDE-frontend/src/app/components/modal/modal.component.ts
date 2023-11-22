import {
  Component,
  ContentChild,
  ElementRef,
  EventEmitter,
  Input,
  Output,
  Renderer2,
  TemplateRef,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
  host: {
    class: 'z-50',
  },
  /*   animations: [
    trigger('slideIn', [
      transition(':enter', [
        style({ opacity: 0, scale: 0.75 }),
        animate('200ms ease-in', style({ opacity: 1, scale: 1 })),
      ]),
    ]),
  ], */
})
export class ModalComponent {
  @Input() showModal: boolean = false;
  @Input() title: string = 'Modal title';
  @Input() maxWidth: string | undefined; // Define the maxWidth input property

  @Output() closeModal: EventEmitter<any> = new EventEmitter();
  constructor(private renderer: Renderer2, private el: ElementRef) {}

  @ContentChild('footer') footerContent!: TemplateRef<any>;

  onCloseModal() {
    this.closeModal.emit();
  }

  getMaxWidth(): string {
    const maxWidthMap: { [key: string]: string } = {
      sm: 'sm:max-w-sm',
      md: 'sm:max-w-md',
      lg: 'sm:max-w-lg',
      xl: 'sm:max-w-xl',
      '2xl': 'sm:max-w-2xl',
    };
    return maxWidthMap[this.maxWidth || 'lg'];
  }
}
